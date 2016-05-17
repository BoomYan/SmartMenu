import org.apache.spark.storage.StorageLevel._
import org.apache.spark.mllib.recommendation._

object Predicter {
  	def main(args: Array[String]) {

	  	//set aws conf
		sc.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")  
		sc.hadoopConfiguration.set("fs.s3a.access.key", "AKIAJEGWABLP254VT75Q")
		sc.hadoopConfiguration.set("fs.s3a.secret.key", "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")

		/*
		* Predict Score Phase
		*/
		// load a persisted model
		val model = MatrixFactorizationModel.load(sc, "s3a://com.smartmenu.data/smartMenuModel")

		val toPredictPath = "s3a://com.smartmenu.data/toPredicts.txt";
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


		val savePath = "s3a://com.smartmenu.data/predictions/" + userID + "_" + restaurantID;

		val predictions = model.predict(userToPredictDishes).
			coalesce(1,true).
			saveAsTextFile(savePath)
	}
}






















