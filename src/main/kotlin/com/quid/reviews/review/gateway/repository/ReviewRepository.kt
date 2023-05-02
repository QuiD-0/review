package com.quid.reviews.review.gateway.repository

import com.quid.reviews.review.domain.Review
import com.quid.reviews.review.gateway.repository.mongoDB.MongoReviewRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

interface ReviewRepository {
    fun save(review: Review): Review
    fun findAll(): List<Review>
    fun findById(id: String): Review

    @Repository
    class ReviewRepositoryImpl(
        private val mongoRepository: MongoReviewRepository
    ) : ReviewRepository {

        override fun save(review: Review) = mongoRepository.save(ofReview(review)).toReview()

        override fun findAll(): List<Review> = mongoRepository.findByDeletedFalse().map { it.toReview() }

        override fun findById(id: String): Review =
            mongoRepository.findByIdOrNull(id)?.toReview() ?: throw Exception("Review not found")
    }
}