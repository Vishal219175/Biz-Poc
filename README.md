Steps For Mongo and Solr Setup :- 
* Mongo Setup
1) Download the Mongodb, MongoDB Compass and MongoDB NoSQL Booster, Mongo shell from Google chrome / EDGE

2) Go to Installation Directory of Mongodb for example :- C:\Program Files\MongoDB\Server\8.0\bin
Update the mongod.conf file :- for example with this and remember Replication setting is needed for solr syncing to work 

# Where and how to store data.
storage:
  dbPath: C:\\Program Files\\MongoDB\\Server\\8.0\\data

# Where to write logging data.
systemLog:
  destination: file
  logAppend: true
  path: C:\\Program Files\\MongoDB\\Server\\8.0\\log\\mongod.log

# Network interfaces
net:
  port: 27017
  bindIp: 127.0.0.1

# Replication settings
replication:
  replSetName: rs0

3) Go to Mongo Shell :- 

Step 1) Paste the connection String you used in the application properties to connect o database for example :- mongodb://localhost:27017/mymvcdba

Step 2) Initialize the Replica for syncing using this command :- rs.initiate()

Step 3) Check the status of Replica :- rs.status()

* Solr Setup
1) Download Solr and solr only works on JDK 1.8/ Java 8 so Update the path variable of Java to Java 8

2) Go to the directory where you have installed solr and run this command to start the solr :-

For example :- C:\Users\Vishal Nagdev\Downloads\solr-8.11.4\solr-8.11.4\bin>
to start the solr :- solr start 

to stop the solr :- solr stop -p 8983

to restart the solr :- solr restart -p 8983


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

