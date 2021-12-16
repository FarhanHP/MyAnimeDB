package com.farhanhp.myanimedb.datas

data class AnimeScoreResponse(
  val animeId: String,
  val newAverageScore: Double,
  val newUserCount: Int
)
