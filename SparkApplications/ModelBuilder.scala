import org.apache.spark.storage.StorageLevel._
import org.apache.spark.mllib.recommendation._


object ModelBuilder {
	def main(args: Array[String]) {
  		//set aws conf
		sc.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")  
		sc.hadoopConfiguration.set("fs.s3a.access.key", "")
		sc.hadoopConfiguration.set("fs.s3a.secret.key", "")

		/*
		* Preparing Data Phase
		*/

		// load raw User-Artist data
		val rawUserArtistData = sc.textFile("s3a://com.smartmenu.data/user_dish_logs.txt")
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
		model.save(sc, "s3a://com.smartmenu.data/smartMenuModel")
	}
}
















