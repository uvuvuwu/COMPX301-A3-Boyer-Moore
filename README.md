# COMPX301-A3-Boyer-Moore

### MakeBMTable

Usage: `java MakeBMTable <String to find> <BMTable file name>`

Generates a Boyer Moore skip table for BMSearch

### BMSearch

Usage: `java BMSearch <BMTable.txt> <SearchFile.txt>`

Will output all lines in `SearchFile.txt` which contain a matching string
(as defined in `BMTable.txt`) to stdout.