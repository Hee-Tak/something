fun main(){
1
    //val today: Int = (1..3).random()
    //1: 맑음 , 2: 흐림, 3: 비
    println("1: 맑음\t2: 흐림\t 3: 비")
    var input: Int? = null
    while(input !in 1..3){
        print("오늘의 날씨를 선택하시오 (1, 2, 3)")
        input =  readLine()?.toIntOrNull()

        if(input !in 1..3){
            println("잘못된 입력입니다. 올바른 값을 입력해주세요.")
        }
    }
    var yesterday: String? = null
    var today: String = "sunny"
    when(input){
        1 -> today = "sunny"
        2 -> today = "cloudy"
        3 -> today = "rainy"
    }

    val days = readLine()?.toInt() ?: 0
    val array = DoubleArray(days)


    val todayWeather = "sunny"
    val daysLater = 3

    val probability = calculateProbability(0, todayWeather, daysLater)
    println("확률: $probability")


}


fun TWP(yesterday: String?, today: String): Triple<Double, Double, Double> { //Tomorrow Weather Prediction : TWP
    var sunnyProb = 0.0
    var cloudyProb = 0.0
    var rainyProb = 0.0

    when(today){
        "sunny" -> {
            sunnyProb = 0.7
            cloudyProb = 0.2
            rainyProb = 0.1
        }
        "cloudy" -> {
            when(yesterday){
                "sunny" -> {
                    sunnyProb = 0.5
                    cloudyProb = 0.4
                    rainyProb = 0.1
                }
                else -> {
                    sunnyProb = 0.3
                    cloudyProb = 0.4
                    rainyProb = 0.3
                }
            }
        }
        "rainy" -> {
            sunnyProb = 0.3
            cloudyProb = 0.3
            rainyProb = 0.4
        }
    }
    return Triple(sunnyProb, cloudyProb, rainyProb)
}

fun calculateProbability(currentDay: Int, currentWeather: String, remainingDays: Int): Double {
    // Base case: 남은 일수가 0이면 최종 결과 반환
    if (remainingDays == 0) {
        // 3일 후이고 현재 날짜가 0이며 현재 날씨가 "맑음"인 경우에만 "비"가 되는 경우의 확률을 반환
        return if (currentDay == 3 && currentWeather == "sunny") 1.0 else 0.0
    }

    // 다음 날의 날씨로 가능한 경우의 수와 해당 확률을 구함
    val twp = TWP(if (currentDay == 0) null else currentWeather, currentWeather)
    val sunnyProb = twp.first
    val cloudyProb = twp.second
    val rainyProb = twp.third

    // 다음 날의 날씨별로 재귀 호출하여 확률을 계산
    var probability = 0.0
    probability += sunnyProb * calculateProbability(currentDay + 1, "sunny", remainingDays - 1)
    probability += cloudyProb * calculateProbability(currentDay + 1, "cloudy", remainingDays - 1)
    probability += rainyProb * calculateProbability(currentDay + 1, "rainy", remainingDays - 1)

    return probability
}


/**
 * 기본 시작
 * - 오늘 날씨를 기준으로 n 일 후에 비가 올 확률을 예측하는 프로그램
 * - 날씨 상태는 맑음 / 흐림 / 비
 * 1. 오늘 날씨가 맑음이라면 내일 흐릴 확률은 20%, 비가 올 확률은 10%
 * 2. 어제 맑은 날씨였고 오늘 흐린 날씨라면 내일 맑은 날씨일 확률은 50%, 비가 올 확률은 10%
 * 3. 어제 맑지 않은 날씨였고, 오늘 흐린 날씨라면 내일 맑은 날씨일 확률은 30%, 비가 올 확률은 30%
 * 4. 오늘 비가 왔다면 내일 맑을 확률은 30%, 흐릴 확률은 30%
 */