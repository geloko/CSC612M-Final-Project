import numpy as np
from datetime import datetime
from datetime import timedelta

input = "banana$"

start_time = datetime.now()
print("Started BWT at:", start_time)

# Step 1 & 2 - List and sort all of the rotations of the input string in aphabetical/lexicographical order
rotations = sorted(range(len(input)), key=lambda i: input[i:]) 
    
# Step 3 - Get the last characters of the sorted rotations
bwt = [input[i - 1] if(i > 0) else input[len(input) - 1] for i in rotations]

end_time = datetime.now()
print("Finished BWT at:", end_time)

runtime = end_time - start_time
print("BWT runtime in seconds:", runtime.seconds, ".", runtime.microseconds)

print("\nBWT:", "".join(bwt))

substring = 'ana'

# Perform FM-Index compression
fm_index = sorted(bwt)
for i in range(1, len(substring)):
    print("\nPass #", i)
    fm_index = sorted(fm_index)
    fm_index = [x + y for x, y in zip(bwt, fm_index)]
    
    for j in range(len(bwt)):
        print(fm_index[j])
        
# FM-Index Count - get the number of occurences of the substring within the compressed text
substring_count = fm_index.count(substring)
print("\nNumber of substrings present:", substring_count)

# FM-Index Locate - locate the position(s) of the substring within the compressed text
substring_index = []
if(substring_count > 0):
    substring_index = [i for i, j in enumerate(fm_index) if j == substring]
    print("Index/Indices where the substring is present:", substring_index)