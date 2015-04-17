# Java Developer Test
This is my solution to the challenge to develop an executable jar file that makes an API request and writes the abridged response to a csv file. on running:
```
java -jar GoEuroTest.jar "CITY_NAME"
```
A file called resonse.csv is created if the query yeilds results.
At the heart of the solution is a regular expression to parse the API response and create the csv. No external JSON and CSV libries have been used.
