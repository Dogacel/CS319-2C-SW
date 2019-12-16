import os

count = 1
with open('cards.txt') as f:
    flines = f.readlines()
    for line in flines:
        fn = line.split('.')[0].replace(' ', '_') + '.json'
        with open(fn, 'r') as nf:
            lines = nf.readlines()

        lines[1] = lines[1].split(':')[0] + ': ' + str(count) + ',\n'

        with open(fn, 'w') as nf:
            nf.writelines(lines)

        count += 1