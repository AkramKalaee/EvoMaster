package org.evomaster.core.search


class Randomness {

    private val random = java.util.Random()

    constructor(seed: Long){
        random.setSeed(seed)
    }


    fun nextBoolean() = random.nextBoolean()

    fun nextInt() = random.nextInt()

    fun nexInt(bound: Int) = random.nextInt(bound)

    fun <T> choose(list : List<T>) : T {
        if(list.isEmpty()){
            throw IllegalArgumentException("Empty list to choose from")
        }
        val index = random.nextInt(list.size)
        return list[index]
    }
}