package infosys.com.kotlinmvvmsample.service.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

// Fact Data Class
@Entity
class Fact(@PrimaryKey(autoGenerate = true)
           val id: Int,
           var title: String? = null,
           var description: String? = null,
           var imageHref: String? = null
)