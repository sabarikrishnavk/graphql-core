Steps for Promo calculation
--
PromotionEngine : calculateCart(PEOrder extends PERequest) : PEResult

1. PromotionService.activeSKURules : 
   Find all item/category Promotions for each SKUS in a group /location
   
   a)Get all active promotions and promotion attributes -WHEN Conditions
       (item.attributes ,category attributes)
  
   b) Get all sku and its category attributes.
   
   c) Filter out eligible promotions for the sku group.

2. SKUPromotionCalculator: Evaluate Promotions based on promotion THEN Conditions

    a) Prioritize the promotions ,create an index based on priority
    
    b) Calculate SKU discounts
   
3. PromotionService.activeOrderRules : 
   FindPromotions: Find eligible order and shipping promotions.

4. ShippingPromotionCalculator: 
    Calculate shipping discounts
5. OrderPromotionCalculator:
   Calculate shipping discounts
   


Refer
https://github.com/gabrielstelmach/itfromhell/blob/master/dynamicdrools/src/main/resources/drools/templates/Product.drl

