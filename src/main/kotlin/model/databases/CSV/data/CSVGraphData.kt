package model.databases.CSV.data

data class CSVGraphData(

    var isNode: Boolean,
    var name: String,
    var x: Double?,
    var y: Double?,
    var color: String?,
    var community: Int?,

    var from: String?,
    var to: String?
)
