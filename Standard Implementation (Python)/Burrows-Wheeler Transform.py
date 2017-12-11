from datetime import datetime


def merge(input, left, right, rotations):
    x = 0
    y = 0

    for i in range(len(rotations)):
        if y >= len(right) or (x < len(left) and (input[left[x]] < input[right[y]])):
            rotations[i] = left[x]
            x += 1

        elif x < len(left) and (input[left[x]] == input[right[y]]):
            j = 1

            while input[(left[x] + j) % len(input)] == input[(right[y] + j) % len(input)]:
                j += 1

            if input[(left[x] + j) % len(input)] < input[(right[y] + j) % len(input)]:
                rotations[i] = left[x]
                x += 1

            else:
                rotations[i] = right[y]
                y += 1

        else:
            rotations[i] = right[y]
            y += 1


def merge_sort(input, rotations):
    if len(rotations) >= 2:
        middle = int(len(rotations) / 2)
        left = rotations[:middle]
        right = rotations[middle:]

        merge_sort(input, left)
        merge_sort(input, right)
        merge(input, left, right, rotations)


def get_bwt(input, rotations):
    start_time = datetime.now()
    print("Started BWT at:", start_time)

    # Step 1 & 2 - List and sort all of the rotations of the input string in aphabetical/lexicographical order
    merge_sort(input, rotations)

    # Step 3 - Get the last characters of the sorted rotations
    bwt = [input[i - 1] if i > 0 else input[len(input) - 1] for i in rotations]

    end_time = datetime.now()
    print("Finished BWT at:", end_time)

    runtime = end_time - start_time
    print("\nBWT runtime in seconds:", runtime.seconds, ".", runtime.microseconds)

    print("\nBWT:", "".join(bwt))

    return "".join(bwt)


def get_fm_index(bwt, substring):
    # Perform FM-Index compression
    fm_index = sorted(bwt)
    print(fm_index)

    for i in range(1, len(substring)):
        fm_index = sorted(fm_index)
        fm_index = [x + y for x, y in zip(bwt, fm_index)]

        print("\nPass #", i)
        for j in range(len(bwt)):
            print(fm_index[j])

    # FM-Index Count - get the number of occurences of the substring within the compressed text
    substring_count = fm_index.count(substring)
    print("\nNumber of substrings present:", substring_count)

    # FM-Index Locate - locate the position(s) of the substring within the compressed text
    #substring_index = []
    if substring_count > 0:
        substring_index = [i for i, j in enumerate(fm_index) if j == substring]
        print("Index/Indices where the substring is present:", substring_index)

    return substring_count#, substring_index


# MAIN
file = open("E_coli.txt", "r")
dna = "".join(file.read().splitlines()) + "$"
arr = [i for i in range(len(dna))]

print(len(dna))

bwt = get_bwt(dna, arr)
count, locate = get_fm_index(bwt, "ana")
