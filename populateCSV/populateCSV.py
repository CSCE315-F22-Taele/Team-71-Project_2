import csv
import random

order_nums = {
    1:5.09,
    2:5.79,
    3:5.39,
    4:6.09,
    5:5.19,
    6:7.09,
    7:5.49,
    8:6.95,
    9:6.75,
    10:5.99,
    11:8.45,
    12:8.59,
    13:8.29,
    14:3.79,
    15:4.85

}
dates = ["10/3/2022", "10/2/2022", "10/1/2022", "09/30/2022", "09/29/2022", "09/28/2022", "09/27/2022",
         "09/26/2022", "09/25/2022", "09/24/2022", "09/23/2022","09/22/2022","09/21/2022","09/20/2022"]

servers = ["server1", "server2"]

file = open('revenue.csv', 'w+', newline="")
writer = csv.writer(file)
#writer.writerow(["id", "cost", "date", "server"])

for i in range(0,14):
    for j in range(0,150):
        random_order = random.choice(list(order_nums))
        cost = order_nums[random_order]
        random_server = random.choice(list(servers))
        random_row = [random_order, cost, dates[i], random_server]

        writer.writerow(random_row)



