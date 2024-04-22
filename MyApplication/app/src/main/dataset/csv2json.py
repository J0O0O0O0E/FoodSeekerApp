import csv
import json

# 要转换为数字的字段列表
numeric_fields = ['index', 'id', 'lat', 'lon', 'food1_pasta', 'food2_bread', 'food3_milk', 'food4_pie', 'food5_vet', 'capacity', 'rating']
integer_fields = ['food1_pasta', 'food2_bread', 'food3_milk', 'food4_pie', 'food5_vet', 'capacity']

# 打开CSV文件并读取数据
with open('dataset.csv', mode='r', encoding='utf-8') as file:
    reader = csv.DictReader(file)
    data = []
    for row in reader:
        # 将status字段转换为布尔值，默认为True
        row['status'] = True if row.get('status', 'true').lower() == 'true' else False

        # 遍历每个字典，将指定字段转换为浮点数或整数
        for field in numeric_fields:
            if field in row and row[field] != '':
                try:
                    # 对特定字段进行整数转换
                    if field in integer_fields or field in ['index', 'id']:
                        row[field] = int(row[field])
                    else:
                        row[field] = float(row[field])
                except ValueError:
                    # 如果转换失败（如空字符串等），保留原始值或设定为None或其他适当的值
                    row[field] = None
        data.append(row)

# 将数据转换为JSON格式
with open('dataset.json', mode='w', encoding='utf-8') as jsonfile:
    jsonfile.write(json.dumps(data, indent=4))
