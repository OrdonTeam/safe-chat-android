package com.safechat.encryption


fun <T> List<T>.split(partitionSize: Int): List<List<T>> {
    val numberOfParts = size / partitionSize
    return (0..numberOfParts)
            .map { partNumber -> partNumber * partitionSize }
            .map { offset -> offset..rightEdge(offset + partitionSize, size) - 1 }
            .map { indexesRange -> slice(indexesRange) }
}

private fun rightEdge(calculatedLastIndex: Int, size: Int) = if (calculatedLastIndex > size) size else calculatedLastIndex
