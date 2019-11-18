from collections import OrderedDict
import json

id = 0
with open("cards.txt") as cards:
    for line in cards:
        splitted = line.split(".")
        name = splitted[0]

        resources = splitted[1]

        requirements = {}

        if resources != "none":
            resources = resources.split(",")

            for resource in resources:
                no_and_name = resource.split("_")
                no = int(no_and_name[0])
                resource_name = no_and_name[1]

                requirements[resource_name] = no
        
        chains = splitted[2]
        chain = []

        if chains != "none":
            chains = chains.split(",")

            for building in chains:
                chain.append(building)
        
        id = id + 1

        cardEffect = {
            "cardType" : "",
            "victoryPoints" : 0,
            "gold" : 0,
            "shields" : 0,
            "resources" : {

            }
        }


        outputOrdered = OrderedDict( 
            [ ("id", id),
            ("name", name),
            ("requirements", requirements),
            ("buildingChain", chain),
            ("cardEffect", cardEffect ) ]
        )

        name = name.replace(" ", "_")

        with open(name + ".json", 'w') as outfile:
            json.dump(outputOrdered, outfile, indent= 2, default= True)
