import numpy as np
from datetime import datetime
from datetime import timedelta

class BurrowsWheeler(object):

	def getBWT(self, input):
		rotations = []

		start_time = datetime.now()
		print("Started BWT at:", start_time)

		# Step 1 - List all possible rotations of the string
		for i in range(len(input)):
		    rotations.append(input[i:] + input[:i])

		# Step 2 - Sort the list of rotations in aphabetical/lexicographical order
		bwt = sorted(rotations)
		    
		# Step 3 - Get the last characters of the sorted rotations
		last_column = [row[-1:] for row in bwt]

		end_time = datetime.now()
		print("Finished BWT at:", end_time)

		runtime = end_time - start_time
		print("\nBWT runtime in seconds:", runtime.seconds, ".", runtime.microseconds)

		print("\nBWT:", "".join(last_column))

		for i in range(len(input)):
		    print(rotations[i])   
		
		print('')
		
		for i in range(len(input)):
		    print(bwt[i])

		return runtime, "".join(last_column)

	def getFMIndex(self, last_column, substring):
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
		    print("Index/Indices where the substring is present:", substring_index)

		return substring_count, substring_index
			