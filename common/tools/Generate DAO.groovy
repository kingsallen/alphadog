import com.intellij.database.model.DasTable
import com.intellij.database.model.ObjectKind
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

import java.time.LocalDate

/*
 * 自动生成DAO
 */

packageName = "com.moseeker.baseorm.dao"
typeMapping = [
        (~/(?i)int/)                      : "long",
        (~/(?i)float|double|decimal|real/): "double",
        (~/(?i)datetime|timestamp/)       : "java.sql.Timestamp",
        (~/(?i)date/)                     : "java.sql.Date",
        (~/(?i)time/)                     : "java.sql.Time",
        (~/(?i)/)                         : "String"
]

FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    SELECTION.filter { it instanceof DasTable && it.getKind() == ObjectKind.TABLE }.each { generate(it, dir) }
}

def generate(table, dir) {
    def tableLike = table.getName().toUpperCase()
    def dbName = table.getDbParent().getName()
    def className = javaName(table.getName(), true)
    def fields = calcFields(table)
    new File(dir, className + "Dao.java").withPrintWriter { out -> generate(out, dbName, tableLike, className, fields) }
}

def generate(out, dbName, tableLike, className, fields) {
    out.println "package ${packageName}.${dbName};"
    out.println ""
    out.println "import org.springframework.stereotype.Repository;"
    out.println ""
    out.println "import com.moseeker.baseorm.db.${dbName}.tables.${className};"
    out.println "import com.moseeker.baseorm.db.${dbName}.tables.records.${className}Record;"
    out.println "import com.moseeker.baseorm.crud.JooqCrudImpl;"
    out.println "import com.moseeker.thrift.gen.dao.struct.${dbName}.${className}DO;"
    out.println ""
    out.println "/**"
    out.println "* @author xxx"
    out.println "* ${className}Dao 实现类 （groovy 生成）"
    out.println "* ${LocalDate.now()}"
    out.println "*/"
    out.println "@Repository"
    out.println "public class ${className}Dao extends JooqCrudImpl<${className}DO, ${className}Record> {"
    out.println ""
    out.println ""
    out.println "   public ${className}Dao() {"
    out.println "        super(${className}.${tableLike}, ${className}DO.class);"
    out.println "   }"
    out.println "}"
}

def calcFields(table) {
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
        fields += [[
                           name : javaName(col.getName(), false),
                           type : typeStr,
                           annos: ""]]
    }
}

def javaName(str, capitalize) {
    def s = str.split(/[^\p{Alnum}]/).collect { def s = Case.LOWER.apply(it).capitalize() }.join("")
    capitalize ? s : Case.LOWER.apply(s[0]) + s[1..-1]
}
