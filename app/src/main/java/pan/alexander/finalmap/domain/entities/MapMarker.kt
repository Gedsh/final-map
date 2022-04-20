package pan.alexander.finalmap.domain.entities

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "markers")
data class MapMarker(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "note") val note: String
)
