import csv
import random
from datetime import datetime

def gen_server(fname):
    header = ['SERVER_ID', 'SERVER_NAME', 'MANAGER']
    data = [['001', 'Uchenna', 'P.Taele'], ['002', 'Rhoit', 'P.Taele'], ['003', 'Aazmir', 'P.Taele']]

    with open(fname, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(header)
        writer.writerows(data)

def gen_manager(fname):
    header = ['MANAGER_ID', 'MANAGER_NAME', 'TOTAL_SALES']
    data = [['010', 'P.Taele', '1400']]

    with open(fname, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(header)
        writer.writerows(data)

def gen_orders(fname):
    header =  ['ORDER_ID', 'SERVER_NAME', 'ORDER_TIME']
    servers =  ["Uchenna", "Rohit", "Aazmir"]
    with open(fname, 'w', newline='') as file:
        writer = csv.writer(file)
        for x in range(1400):
            server = str(random.choice(servers))
            now = datetime.now()
            c_time = now.strftime("%H:%M:%S")
            o_id = random.randint(1111, 9999)
            data = [o_id, server, c_time]
            writer.writerow(header)
            writer.writerow(data)




    

def main():
    print("hello")
    gen_server("server.csv")
    gen_orders("orders.csv")