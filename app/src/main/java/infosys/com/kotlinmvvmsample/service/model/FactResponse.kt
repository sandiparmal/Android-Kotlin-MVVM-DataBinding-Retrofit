package infosys.com.kotlinmvvmsample.service.model

data class FactResponse(var title: String,
                        var rows : MutableList<Fact>)