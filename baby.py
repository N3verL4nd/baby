import requests
from datetime import datetime
from openpyxl import Workbook

# 请求 URL 和授权头
url = "https://kid-app-api.ancda.com/v1/moments/list"
headers = {
    "Authorization": "7VuGfcaMokM2yiiOVLOHy0Hzm-auuleeXXVvdLGQ3DQ="  # 请替换为有效的 Authorization token
}

# 初始化 Excel 文件
wb = Workbook()
ws = wb.active
ws.append(["日期", "标题", "链接"])  # 设置表头

# 初始请求体
data = {
    "lastMomentId": "0",
    "momentCategory": 1,
    "classId": "2"
}

# 循环获取数据并保存到 Excel
while True:
    # 发送 POST 请求
    response = requests.post(url, headers=headers, json=data)

    if response.status_code != 200:
        print(f"请求失败，状态码：{response.status_code}")
        break

    # 解析返回的 JSON 数据
    response_data = response.json()

    if "data" not in response_data or not response_data["data"]:
        print("没有更多数据，退出循环。")
        break

    # 获取返回的 data 数组
    moments = response_data["data"]

    # 遍历每一个 moment 并保存到 Excel
    for moment in moments:
        create_time = datetime.utcfromtimestamp(moment.get("createTime")).strftime('%Y-%m-%d %H:%M:%S')
        moment_id = 'https://baby-mobile.ancda.com/moment/detail?instId=78971410344640563&id=' + moment.get("momentId")
        content = moment.get("content")
        ws.append([create_time, content, moment_id])  # 写入 Excel

    # 更新 lastMomentId 为最后一个 momentId
    data["lastMomentId"] = moments[-1]["momentId"]

    print(f"已保存 {len(moments)} 条数据，更新 lastMomentId 为 {data['lastMomentId']}")

# 保存 Excel 文件
wb.save("moments_data.xlsx")
print("数据保存完成，已生成 moments_data.xlsx 文件。")
