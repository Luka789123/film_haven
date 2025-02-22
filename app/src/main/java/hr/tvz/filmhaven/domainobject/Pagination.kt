package hr.tvz.filmhaven.domainobject

interface Pagination {
    suspend fun getNextPage(genre:Genre?):List<FeaturedMovie>
    suspend fun getInitialData(genre: Genre?):List<FeaturedMovie>
}