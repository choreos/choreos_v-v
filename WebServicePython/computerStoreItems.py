# -*- coding: utf-8 -*-

import soaplib

from soaplib.core.model.primitive import String, Integer, Float
from soaplib.core.model.clazz     import Array, ClassModel

class Item(ClassModel):
    category = String
    brand = String
    model = String
    price = Float

    def __init__(self, cat, brand, model, price):
        self.category = cat
        self.brand = brand
        self.model = model
        self.price = price

items = []
items.append(Item("motherboard", "Asus", "NF8V", 102.03))
items.append(Item("motherboard", "Foxconn", "fx566t", 81.15))
items.append(Item("notebook", "Lenovo", "Z360", 850.00))
items.append(Item("notebook", "Lenovo", "Z460", 769.00))
items.append(Item("mouse", "Razor", "RZG145", 89.20))
items.append(Item("mouse", "Clone", "CCCC", 61.00))
items.append(Item("mouse", "Microsoft", "MS23F", 61.00))
