package com.quid.reviews.image.usecase

import com.quid.reviews.image.ImageProcessor
import com.quid.reviews.review.gateway.repository.ReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletResponse

@FunctionalInterface
interface DownloadImage {
    fun download(id: String, response: HttpServletResponse)

    @Service
    @Transactional(readOnly = true)
    class DownloadImageUseCase(
        private val reviewRepository: ReviewRepository,
    ) : DownloadImage {
        override fun download(id: String, response: HttpServletResponse) =
            reviewRepository.findById(id).let {
                    when (it.getImgSize()) {
                        1 -> ImageProcessor.download(it.imgList[0], response)
                        in 2..5 -> ImageProcessor.download(it.imgList, response)
                        else -> throw Exception("Image size is not valid")
                    }
                }
    }
}