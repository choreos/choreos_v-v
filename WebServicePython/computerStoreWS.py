#!/usr/bin/env python
# -*- coding: utf-8 -*-
#

import soaplib

from soaplib.core.server  import wsgi
from soaplib.core.service import soap
from soaplib.core.service import DefinitionBase

from soaplib.core.model.primitive import String, Integer, Float
from soaplib.core.model.clazz     import Array, ClassModel

import computerStoreItems
from computerStoreItems import Item

'''
ComputerHardware store web service
'''
class InfoStoreService(DefinitionBase):

    @soap(String, _returns=Array(Item))
    def search_by_brand(self, brand):
        '''
        Search product by Brand

        @param name of the brand to search for
        @return the completed list
        '''
        results = []
        for i in computerStoreItems.items:
            if i.brand == brand:
                results.append(i)
        return results


    @soap(String, _returns=Array(Item))
    def search_by_category(self, category):
        '''
        Search product by Category

        @param name of the category to search for
        @return the completed list
        '''
        results = []
        for i in computerStoreItems.items:
            if i.category == category:
                results.append(i)
        return results


    @soap(String, _returns=Item)
    def search_by_model(self, model):
        '''
        Search product by Model

        @param name of the model to search for
        @return the item
        '''
        for i in computerStoreItems.items:
            if i.model == model:
                return i
        return None

    @soap(String, _returns=Float)
    def get_mean_price(self, category):
        '''
        Average price of items of a Category

        @param name of the category to search for
        @return the average price
        '''

        mean = 0
        count = 0
        for i in computerStoreItems.items:
            if i.category == category:
                mean += i.price
                count += 1
        if count > 0:
            mean = mean / count
        return mean


    @soap(String, _returns=String)
    def get_cheapest(self, category):
        '''
        The name of the chepeast item of a category
        OBS: if two or more items have the same price, 
        the first one found is returned

        @param name of the category to search for
        @return the model of the chepeast item
        '''
        category_items = [i for i in computerStoreItems.items if i.category == category]
        if len(category_items) > 0:
            a = min(category_items, key=lambda k: k.price)
            item_model = a.model 
        else:
            raise Exception("No item in this category")
        return item_model

if __name__=='__main__':
    try:
        from wsgiref.simple_server import make_server
        soap_application = soaplib.core.Application([InfoStoreService], 'tns')
        wsgi_application = wsgi.Application(soap_application)

        print "listening to http://0.0.0.0:7789"
        print "wsdl is at: http://localhost:7789/?wsdl"

        server = make_server('localhost', 7789, wsgi_application)
        server.serve_forever()

    except ImportError:
        print "Error: example server code requires Python >= 2.5"
