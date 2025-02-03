Queries to use in Mongodb :- 

1. Find All Products:
   db.products.find({})

2. Find All Products with Pretty Formatting:
   db.products.find({}).pretty()

3. Insert a New Product:
   db.products.insertOne({
       "id": "67a05c87b21a86022263ce55",
       "name": "Pdf Doc 2",
       "description": "It is a Pdf Document",
       "price": 100.0,
       "createdOn": ISODate("2025-02-03T06:04:55.314+00:00"),
       "updatedOn": ISODate("2025-02-03T06:06:31.688+00:00")
   })

4. Find a Product by ID:
   db.products.findOne({ id: "67a05c87b21a86022263ce55" })

5. Update a Product by ID:
   db.products.updateOne(
       { id: "67a05c87b21a86022263ce55" },
       {
           $set: {
               name: "Updated Pdf Doc",
               description: "Updated description for the PDF Document",
               price: 120.0,
               updatedOn: ISODate("2025-02-03T06:10:00.000+00:00")
           }
       }
   )

6. Delete a Product by ID:
   db.products.deleteOne({ id: "67a05c87b21a86022263ce55" })

7. Delete All Products:
   db.products.deleteMany({})

8. Find Products Based on Price Condition:
   db.products.find({ price: { $gt: 50 } })

9. Count the Number of Products:
   db.products.countDocuments({})

10. Find and Sort Products by Price:
    // Ascending order (cheapest first)
    db.products.find({}).sort({ price: 1 })

    // Descending order (most expensive first)
    db.products.find({}).sort({ price: -1 })

11. Find a Product and Update Based on Condition:
    db.products.updateMany(
        { price: { $gt: 50 } },
        { $set: { updatedOn: ISODate("2025-02-03T06:15:00.000+00:00") } }
    )

12. Delete All Products with a Price Below 50:
    db.products.deleteMany({ price: { $lt: 50 } })

