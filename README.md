# Littlepay coding test - Zhan Gao
## Tech Stack:
* Maven
* Lombok
* Opencsv
## Assumptions on data:
According to realistic scenarios, all the records are sorted by the tap-on time in an arbitrary bus.
So to match tapping records, firstly all the data are divided into different groups with the same PAN, BusId and CompanyId (achieved by hashing with key **"PAN+BusId+CompanyId"**).
After divided, if there is a matching tapping-off record, it must be the one right after the current record. 
If there is a matching record right after current record, the journey can be classified as **COMPLETE** or **CANCELLED** based on the equality of StopIds of the two consecutive records.
If there isn't a matching record, the status of the journey is **INCOMPLETE**.

## Further improvements:
### Big data
If the raw data is too big to be put in a single hashtable, the raw data can be divided into smaller files based on PAN+BusId+CompanyId prior to calculation, so the size of file won't exceed the limitation of memory.
### Non-sorted data
if the raw data is not sorted by the tap-on time, the sorting can be done after dividing the data into different PAN+BusId+CompanyId groups. The time complexity will be O(nLog(n/m)), where **n** is the number of records, and **m** is the number of groups.
