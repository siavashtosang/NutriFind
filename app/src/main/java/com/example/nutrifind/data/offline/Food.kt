package com.example.nutrifind.data.offline

import com.example.nutrifind.R
import com.example.nutrifind.data.model.ApiEdamam
import com.example.nutrifind.data.model.Hits
import com.example.nutrifind.data.model.Recipe


val foodNames: List<String> =
    listOf(
        "Pasta",
        "Spaghetti",
        "Breakfast",
        "Burger",
        "Pizza",
        "Salads",
        "Drinks",
        "Snacks",
        "Sea food",
        "Sandwiches",
        "Ice Cream",
        "Pie",
        "Steak",
        "Fried chicken",
        "Smoothie",
        "Cookie",
        "Fried chicken",
        "Lasagna",
        "Omelet",
        "Muffin"
    )

val topFoods = listOf(
    TopFood(title = "Salad", image = R.drawable.img_salad_120),
    TopFood(title = "Pizza", image = R.drawable.img_pizza_120),
    TopFood(title = "Chinese", image = R.drawable.img_chinese_120),
)

val fakeFoodData = ApiEdamam(
    hits = MutableList(4) {
        Hits(
            recipe = Recipe(
                calories = 50.4,
                image = "https://edamam-product-images.s3.amazonaws.com/web-img/bb5/bb5bad0cbcb94ad2ef0895d444f30291.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEBgaCXVzLWVhc3QtMSJHMEUCIQDbdebv5mL8hldZVomrKpJZ0zgBcnuf83UMwxU6mIEQsAIgPoRVCFpf1PZ4%2FKNY6GeLrBa6sL9EPFVWIzN6eJ5CqPwqzAQIcRAAGgwxODcwMTcxNTA5ODYiDCeHqtumAv4IYeqmwyqpBMnv1qb%2FAb0DWVI%2FJY1us4oiPItd0%2B%2FqkfF1Epaub31q7norr%2B8uklZsre0L0iJijmlcdVaHRnTX7lBCZLy9bmullakA94617dr9sQm298nwdnymWDjLjzyAMn6TM83W%2FedVHjof5cINRGNfvJOY11sbc4JF8Pt3r1jH9ZQkWe2MKal0iRXTTQhnH2iEh5YZEy4kmdpaMBqlFX8B0tcLN%2F6fiILVzGrUPHFF99skddQwm%2BsgAZ1HNqqGWtOqA0YYdXDvauy4q6wbc2C2j21%2B1vKyNNDgwOjQPp5pz2pLuYccLPuiM8rzeh2EkK6BHzOTh8Mpwk2CbSg6i3Q4bv8D6TfL2DOK4lyh%2B3sWN%2F1yvujFtXXBLk69hivtFWvofRQMNBr7wdDODU%2FIfkbHGQ%2F%2BaFxrtoR8mxuxVRsMc9jQRJJfPH9S3BvDSqH%2F0iD7Jh6DQ4hyDNSfuhKNdyQxbiQYpt%2FrWeDlDlxtIdllGoKObCu55QbYh4ZHQIsM6XtSgRtWJmYrX5AiYtJe5WWo9Tw6vvvrQOyKopkN7%2F8hpG4OebzxnaLVz3r9lnjoqVfQXuNYKV17rvrg8ILZm0uFW2dd%2BPj1BNckCnzdsX9xOcPLSvpFAnsUrV8BLQh%2FH%2BbZcj7zhLXuqDIGZmwlEpltYHTSHG1oJ5X2lJI%2BsiHV4A9TAYRqbKo9%2FtNvLPHCEoHRJLyNQRo0RiWb8uMsqdxJC0hK7Q2rLBwJe93DKOYw75DvnQY6qQH5hiBp0ofAmzUg0K9MVkb%2FGOObJZzCE%2Bd4iTSE6AD1jpy%2B%2BOvkKOlt2e0tv7QWaiQCa%2B4kRlOjQAWNfPe9uxdMf5Jy5533MKJGHan8ZNDf60jUMkUHxTV3kNZytQTZ614O%2FGEVndsKpqjce4Oy12gDqJcMy%2FOdgPw%2B%2B5fT7PA%2BghB4JcC%2FLSSlmc%2BHkQT4A9qGcrRz1mVqGTxwoGE3YNnp3ckG2LDQ9WRn&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230109T084759Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFGRAHKGNA%2F20230109%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=8a9da1b51f0018a5d8eefab81ee6d669ea3a3cf47af1530c57d862fcdbe29be3",
                ingredientLines = arrayListOf("1", "2", "3"),
                label = "Pasta alla Gracia Recipe",
            )
        )
    }
)

data class TopFood(
    val title: String = "",
    val image: Int = R.drawable.img_pasta,
)