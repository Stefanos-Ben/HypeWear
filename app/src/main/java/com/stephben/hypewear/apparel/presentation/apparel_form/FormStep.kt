package com.stephben.hypewear.apparel.presentation.apparel_form

enum class FormStep {
    PROFILE, // Fields: description, imageUrl, Color
    CATEGORIZATION, // Fields: sex, category, tags
    PRICE_STOCK, // Fields: price, stockPerSize
    MANUFACTURING_METRICS, // Fields: fabric, ecoMetrics, ecoScore, ecoBadges
    REVIEW
}