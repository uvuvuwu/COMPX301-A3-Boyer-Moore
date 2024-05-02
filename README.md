# COMPX301-A3-Boyer-Moore

Implemented by Alma Walmsley (ID 1620155) and Daniel Su (ID 1604960)

### MakeBMTable

Usage: `java MakeBMTable <SearchString> <OutputTable.txt>`

Generates a Boyer Moore skip table for the string `SearchString`
and will output this table to `OutputTable.txt`. The table can then
be used for the BMSearch program.

### BMSearch

Usage: `java BMSearch <BMTable.txt> <SearchFile.txt>`

Will output all lines in `SearchFile.txt` which contain a matching string
(as defined in `BMTable.txt`) to stdout.