package infosys.com.kotlinmvvmsample.service.model

// Fact Response Data Class
data class FactResponse(var title: String,
                        var rows : MutableList<Fact>)