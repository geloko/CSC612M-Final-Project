import numpy as np
from datetime import datetime
from datetime import timedelta

input = 'banana$'
rotations = []

start_time = datetime.now()

# Step 1 - List all possible rotations of the string
for i in range(len(input)):
    rotations.append(input[i:] + input[:i])

# Step 2 - Sort the list of rotations in aphabetical/lexicographical order
bwt = sorted(rotations)
    
# Step 3 - Get the last characters of the sorted rotations
last_column = [row[-1:] for row in bwt]

dt = datetime.now() - start_time
millis = (dt.days * 24 * 60 * 60 + dt.seconds) * 1000 + dt.microseconds / 1000.0

for i in range(len(input)):
    print(rotations[i])   

print('')

for i in range(len(input)):
    print(bwt[i])
    
print("\nBWT:", "".join(last_column))
print("Runtime in milliseconds:", millis)

substring = 'ana'

# Perform FM-Index compression
search = sorted(last_column)
for i in range(1, len(substring)):
    print("\nPass #", i)
    search = sorted(search)
    for j in range(len(last_column)):
        search[j] = last_column[j] + search[j]
        
    for j in range(len(search)):
        print(search[j])

# FM-Index Count - get the number of occurences of the substring within the compressed text
substring_count = search.count(substring)
print("\nNumber of substrings present:", substring_count)

# FM-Index Locate - locate the position(s) of the substring within the compressed text
substring_index = []
if(substring_count > 0):
    substring_index = [i for i, j in enumerate(search) if j == substring]
    print("\nIndex/Indices where the substring is present:", substring_index)