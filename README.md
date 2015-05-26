# Purpose
This is a simple implementation of a server for processing texts(tokenizing, stop-words extraction, stemming and distance measuring). Server is implemented using NodeJS and it's module natural(https://github.com/NaturalNode/natural)

# Stemming algorithms
Current implementation supports only Porter and Lancaster stemming algorithms.

# Stop-words extraction
Now server can extract stop-words only in english language.

# Server
Server extracts text to process from header paramter *doc*. Server output is in JSON format.

Server instance can be configured using following parameters:
  1. **"include_original_doc"**: if specified as 'true' server will output input text
  2. **"include_tokens"**: if specified as 'true' output will containt tokens list
  3. **"exclude_stops"**: if specified as 'true' stop-words will be excluded from output
  4. **"include_non_stops"**:  if specified as 'true' non stop-words will be added to the ouput
  5. **"run_porter"**: if specified as 'true' Porter stemming algorithm will be runned on tokens
  6. **"run_lancaster"**: runs Lancaster stemming algorithm if parameter is specified as 'true'
  7. **"calculate_levenshtine"** and **"calculate_jaro_winkler"**: Calculates distances between stemmed words using Levenshtine and/or Jaro-Winkler string distance metrics if specified as 'true'. This paramters will be used *only* if 'run_porter' and 'run_lancaster' parameters are specified.
  8. **"include_n_grams"**: Accepts a numeric value greater than zero and less than tokens count.

# Plans
Add Java/Python libraries
