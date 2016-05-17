import org.apache.spark.storage.StorageLevel._
import org.apache.spark.mllib.recommendation._

/*
* Preparing Data Phase
*/

// load raw User-Artist data
val rawUserArtistData = sc.textFile("com.smartmenu.data/user_dish_logs.txt")
// creat train data from raw User Artist Data
val trainData = rawUserArtistData.map { line =>
	val Array(userID, artistID) = line.split(',').map(_.toInt) 
	Rating(userID, artistID, 1)
}.persist(MEMORY_AND_DISK_SER)
/*
* train using ALS lib, and get the data model
* this data model has 
* 1: user - feature matrics 2: product - feature matrics
* as RDDs
*/
val model = ALS.trainImplicit(trainData, 10, 5, 0.01, 1.0)
// save a model, the directory can not exist!!
model.save(sc, "com.smartmenu.data/smartMenuModel")

/*
* Predict Score Phase
*/
import org.apache.spark.storage.StorageLevel._
import org.apache.spark.mllib.recommendation._

val model = MatrixFactorizationModel.load(sc, "com.smartmenu.data/smartMenuModel")

val toPredictPath = "com.smartmenu.data/ToPredicts_00005_45b9c8c1f964a520d7411fe3.txt";
val userToPredictDishes = sc.textFile(toPredictPath)
	.map(_.split(',') match {case Array(userID, restaurantID, dishID) =>
		(userID.toInt, dishID.toInt)
}).persist(MEMORY_AND_DISK_SER);

val userID = sc.textFile(toPredictPath)
	.map(_.split(',') match {case Array(userID, restaurantID, dishID) =>
		(userID)
}).take(1)(0).toString
val restaurantID = sc.textFile(toPredictPath)
	.map(_.split(',') match {case Array(userID, restaurantID, dishID) =>
		(restaurantID)
}).take(1)(0).toString


val savePath = "com.smartmenu.data/predictions/" + userID + "_" + restaurantID;


val predictions = model.predict(userToPredictDishes).
	coalesce(1,true).
	saveAsTextFile(savePath)






///other!!!!!!!!!
///dont copy lines below

sc.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")  
sc.hadoopConfiguration.set("fs.s3a.access.key", "AKIAJEGWABLP254VT75Q")
sc.hadoopConfiguration.set("fs.s3a.secret.key", "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")

val rawUserArtistData = sc.textFile("s3a://music-data-test/user_artist_data.txt")







