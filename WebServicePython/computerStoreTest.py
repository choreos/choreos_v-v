#!/usr/bin/env python
# -*- coding: utf-8 -*-
#

import unittest
import math
from computerStoreWS import InfoStoreService
import computerStoreItems

class TestComputerStore(unittest.TestCase):

    def setUp(self):
        self.store = InfoStoreService()
        pass

    def testReturnSomethingbyBrand(self):
        self.assertTrue(len(self.store.search_by_brand("Lenovo")))

    def testCheckModelFromBrand(self):
        items = self.store.search_by_brand("Lenovo")

        for item in items:
            item_by_model = self.store.search_by_model(item.model)
           
            self.assertTrue(item == item_by_model)

    def testCheckModelFromCategory(self):
        items = self.store.search_by_category("mouse")

        for item in items:
            item_by_model = self.store.search_by_model(item.model)
           
            self.assertTrue(item == item_by_model)

    def testGetCheapest(self):
        item_model = self.store.get_cheapest("mouse")

        item = self.store.search_by_model(item_model)
        
        self.assertTrue(math.fabs(computerStoreItems.CHEAPEST_MOUSE_PRICE - item.price) < 1e-9)

    def testMeanPrice(self):

        average_price = self.store.get_mean_price("mouse")

        self.assertTrue(math.fabs(computerStoreItems.AVERAGE_MOUSE_PRICE - average_price) < 1e-9)

if __name__ == '__main__':
    unittest.main()
