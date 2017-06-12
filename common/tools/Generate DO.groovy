import com.intellij.database.model.DasTable
import com.intellij.database.model.ObjectKind
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

/*
 * auth: ltf
 * 根据table自动生成thrift的struct文件
 */

packageName = "java com.moseeker.thrift.gen.dao.struct"
typeMapping = [
        (~/tinyint/)                   : "i8",
        (~/(?i)int/)                      : "i32",
        (~/(?i)float|double|decimal|real/): "double",
        (~/(?i)datetime|timestamp/)       : "string",
        (~/(?i)date/)                     : "string",
        (~/(?i)time/)                     : "string",
        (~/(?i)/)                         : "string"
]

FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    SELECTION.filter { it instanceof DasTable && it.getKind() == ObjectKind.TABLE }.each { generate(it, dir) }
}

def generate(table, dir) {
    def className = javaName(table.getName(), true)
    def fields = calcFields(table)
    def strFile = new File(dir, table.getDbParent().getName() + "_struct.thrift")
    def print = new PrintWriter(new FileWriter(strFile, true))
    if(strFile.length() == 0) {
        print.println "namespace $packageName"
    }
    print.withPrintWriter { out -> generate(out, className, fields) }
}

def generate(out, className, fields) {
    out.println ""
    out.println ""
    out.println "struct ${className}DO {"
    out.println ""
    fields.eachWithIndex { item, index ->
        out.print "\t${index+1}: optional ${item.type} ${item.name}"
        out.print index == fields.size - 1 ? "" : ","
        out.println "\t//${item.comment}"
    }
    out.println ""
    out.println "}"
}

def calcFields(table) {
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
        fields += [[
                           name : col.getName(),
                           type : typeStr,
                           comment : col.getComment()
                   ]]
    }
}

def javaName(str, capitalize) {
    def s = str.split(/[^\p{Alnum}]/).collect { def s = Case.LOWER.apply(it).capitalize() }.join("")
    capitalize ? s : Case.LOWER.apply(s[0]) + s[1..-1]
}
