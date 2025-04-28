package com.example.nutrifind.data.model

import com.google.gson.annotations.SerializedName


data class ApiEdamam(
    @SerializedName("from") var from: Int? = null,
    @SerializedName("to") var to: Int? = null,
    @SerializedName("count") var count: Int? = null,
    @SerializedName("_links") var links: Links? = null,
    @SerializedName("hits") var hits: List<Hits> = emptyList()
)


data class Links(
    @SerializedName("next") var next: Next? = Next(),
    @SerializedName("self") val self: Self? = Self()
)

data class Next(
    @SerializedName("href") var href: String? = null,
    @SerializedName("title") var title: String? = null
)

data class Hits(
    @SerializedName("recipe") var recipe: Recipe? = Recipe(),
    @SerializedName("_links") var links: Links? = Links()
)

data class Recipe(
    @SerializedName("uri") var uri: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("images") var images: Images? = Images(),
    @SerializedName("source") var source: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("shareAs") var shareAs: String? = null,
    @SerializedName("yield") var yield: Double? = null,
    @SerializedName("dietLabels") var dietLabels: List<String> = emptyList(),
    @SerializedName("healthLabels") var healthLabels: List<String> = emptyList(),
    @SerializedName("cautions") var cautions: List<String> = emptyList(),
    @SerializedName("ingredientLines") var ingredientLines: List<String> = emptyList(),
    @SerializedName("ingredients") var ingredients: List<Ingredients> = emptyList(),
    @SerializedName("calories") var calories: Double? = null,
    @SerializedName("totalCO2Emissions") var totalCO2Emissions: Double? = null,
    @SerializedName("co2EmissionsClass") var co2EmissionsClass: String? = null,
    @SerializedName("totalWeight") var totalWeight: Double? = null,
    @SerializedName("totalTime") var totalTime: Int? = null,
    @SerializedName("cuisineType") var cuisineType: List<String> = emptyList(),
    @SerializedName("mealType") var mealType: List<String> = emptyList(),
    @SerializedName("dishType") var dishType: List<String> = emptyList(),
    @SerializedName("totalNutrients") var totalNutrients: TotalNutrients? = TotalNutrients(),
    @SerializedName("totalDaily") var totalDaily: TotalDaily? = TotalDaily(),
    @SerializedName("digest") var digest: List<Digest> = emptyList(),
    @SerializedName("tags") var tags: List<String> = emptyList()
)

data class Images(
    @SerializedName("THUMBNAIL") var thumbnail: THUMBNAIL? = THUMBNAIL(),
    @SerializedName("SMALL") var small: SMALL? = SMALL(),
    @SerializedName("REGULAR") var regular: REGULAR? = REGULAR(),
    @SerializedName("LARGE") var large: LARGE? = LARGE()
)

data class Ingredients(
    @SerializedName("text") var text: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("measure") var measure: String? = null,
    @SerializedName("food") var food: String? = null,
    @SerializedName("weight") var weight: Double? = null,
    @SerializedName("foodCategory") var foodCategory: String? = null,
    @SerializedName("foodId") var foodId: String? = null,
    @SerializedName("image") var image: String? = null
)

data class THUMBNAIL(
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null
)

data class SMALL(
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null
)

data class REGULAR(
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null
)

data class LARGE(
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("height") var height: Int? = null
)

data class TotalNutrients(
    @SerializedName("ENERC_KCAL") var enercKcal: ENERCKCAL? = ENERCKCAL(),
    @SerializedName("FAT") var fat: FAT? = FAT(),
    @SerializedName("FASAT") var fast: FASAT? = FASAT(),
    @SerializedName("FATRN") var fatrn: FATRN? = FATRN(),
    @SerializedName("FAMS") var fams: FAMS? = FAMS(),
    @SerializedName("FAPU") var fapu: FAPU? = FAPU(),
    @SerializedName("CHOCDF") var chocdf: CHOCDF? = CHOCDF(),
    @SerializedName("CHOCDF.net") var chocdfNet: CHOCDFNet? = CHOCDFNet(),
    @SerializedName("FIBTG") var fibtg: FIBTG? = FIBTG(),
    @SerializedName("SUGAR") var sugar: SUGAR? = SUGAR(),
    @SerializedName("PROCNT") var procnt: PROCNT? = PROCNT(),
    @SerializedName("CHOLE") var chole: CHOLE? = CHOLE(),
    @SerializedName("NA") var na: NA? = NA(),
    @SerializedName("CA") var ca: CA? = CA(),
    @SerializedName("MG") var mg: MG? = MG(),
    @SerializedName("K") var k: K? = K(),
    @SerializedName("FE") var fe: FE? = FE(),
    @SerializedName("ZN") var zn: ZN? = ZN(),
    @SerializedName("P") var p: P? = P(),
    @SerializedName("VITA_RAE") var vitaRae: VITARAE? = VITARAE(),
    @SerializedName("VITC") var vitc: VITC? = VITC(),
    @SerializedName("THIA") var thia: THIA? = THIA(),
    @SerializedName("RIBF") var ribf: RIBF? = RIBF(),
    @SerializedName("NIA") var nia: NIA? = NIA(),
    @SerializedName("VITB6A") var vitb6a: VITB6A? = VITB6A(),
    @SerializedName("FOLDFE") var foldfe: FOLDFE? = FOLDFE(),
    @SerializedName("FOLFD") var folfd: FOLFD? = FOLFD(),
    @SerializedName("FOLAC") var folac: FOLAC? = FOLAC(),
    @SerializedName("VITB12") var vitb12: VITB12? = VITB12(),
    @SerializedName("VITD") var vitd: VITD? = VITD(),
    @SerializedName("TOCPHA") var tocpha: TOCPHA? = TOCPHA(),
    @SerializedName("VITK1") var vitk1: VITK1? = VITK1(),
    @SerializedName("WATER") var water: WATER? = WATER()
)

data class TotalDaily(
    @SerializedName("ENERC_KCAL") var enercKcal: ENERCKCAL? = ENERCKCAL(),
    @SerializedName("FAT") var fat: FAT? = FAT(),
    @SerializedName("FASAT") var fasat: FASAT? = FASAT(),
    @SerializedName("CHOCDF") var chocdf: CHOCDF? = CHOCDF(),
    @SerializedName("FIBTG") var fibtg: FIBTG? = FIBTG(),
    @SerializedName("PROCNT") var procnt: PROCNT? = PROCNT(),
    @SerializedName("CHOLE") var chole: CHOLE? = CHOLE(),
    @SerializedName("NA") var na: NA? = NA(),
    @SerializedName("CA") var ca: CA? = CA(),
    @SerializedName("MG") var mg: MG? = MG(),
    @SerializedName("K") var k: K? = K(),
    @SerializedName("FE") var fe: FE? = FE(),
    @SerializedName("ZN") var zn: ZN? = ZN(),
    @SerializedName("P") var p: P? = P(),
    @SerializedName("VITA_RAE") var vitaRae: VITARAE? = VITARAE(),
    @SerializedName("VITC") var vitc: VITC? = VITC(),
    @SerializedName("THIA") var thia: THIA? = THIA(),
    @SerializedName("RIBF") var ribf: RIBF? = RIBF(),
    @SerializedName("NIA") var nia: NIA? = NIA(),
    @SerializedName("VITB6A") var vitb6a: VITB6A? = VITB6A(),
    @SerializedName("FOLDFE") var foldfe: FOLDFE? = FOLDFE(),
    @SerializedName("VITB12") var vitb12: VITB12? = VITB12(),
    @SerializedName("VITD") var vitd: VITD? = VITD(),
    @SerializedName("TOCPHA") var tocpha: TOCPHA? = TOCPHA(),
    @SerializedName("VITK1") var vitk1: VITK1? = VITK1()
)


data class Digest(
    @SerializedName("label") var label: String? = null,
    @SerializedName("tag") var tag: String? = null,
    @SerializedName("schemaOrgTag") var schemaOrgTag: String? = null,
    @SerializedName("total") var total: Double? = null,
    @SerializedName("hasRDI") var hasRDI: Boolean? = null,
    @SerializedName("daily") var daily: Double? = null,
    @SerializedName("unit") var unit: String? = null,
    @SerializedName("sub") var sub: List<Sub> = emptyList()
)

data class FATRN(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class FAMS(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class FAPU(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class CHOCDFNet(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class SUGAR(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class RIBF(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class FOLFD(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class FOLAC(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class WATER(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class ENERCKCAL(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class FAT(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class FASAT(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class CHOCDF(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class FIBTG(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class PROCNT(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class CHOLE(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class NA(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class CA(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class MG(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class K(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class FE(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class ZN(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class P(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class VITARAE(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class VITC(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null

)


data class THIA(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class NIA(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class VITB6A(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class FOLDFE(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class VITB12(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class VITD(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class TOCPHA(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class VITK1(
    @SerializedName("label") var label: String? = null,
    @SerializedName("quantity") var quantity: Double? = null,
    @SerializedName("unit") var unit: String? = null
)

data class Sub(
    @SerializedName("label") var label: String? = null,
    @SerializedName("tag") var tag: String? = null,
    @SerializedName("schemaOrgTag") var schemaOrgTag: String? = null,
    @SerializedName("total") var total: Double? = null,
    @SerializedName("hasRDI") var hasRDI: Boolean? = null,
    @SerializedName("daily") var daily: Double? = null,
    @SerializedName("unit") var unit: String? = null
)


data class Self(
    @SerializedName("href") var href: String? = null,
    @SerializedName("title") var title: String? = null
)