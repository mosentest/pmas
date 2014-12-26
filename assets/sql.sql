        //联系组
        db.execSQL("CREATE TABLE tb_contact_group(" +
                " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +//序号
                " name TEXT NOT NULL UNIQUE," +//组名
                " description CHAR(16));");//描述
        //联系人
        db.execSQL("CREATE TABLE tb_contact(" +
                " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +//序号
                " cg_id INTEGER," +//联系组的外键
                " name TEXT," +//姓名
                " birthday TEXT," +//出生日期
                " phone_one TEXT," +//联系电话1
                " phone_two TEXT," +//联系电话2
                " phone_three TEXT," +//联系电话3
                " email TEXT," +//电邮邮箱
                " address VARCHAR(30)," +//地址
                " FOREIGN KEY(cg_id) REFERENCES tb_contact_group(_id) ON DELETE SET NULL ON UPDATE CASCADE);");//级联操作和外键约束
        //记事类型
        db.execSQL("CREATE TABLE tb_note_group(" +
                " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +//序号
                " name TEXT NOT NULL UNIQUE," +//名称
                " description CHAR(16));");//描述
        //记事
        db.execSQL("CREATE TABLE tb_note(" +
                " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +//序号
                " ng_id INTEGER," +//记事类型外键
                " title TEXT," +//标题
                " create_date TEXT," +//创建日期
                " content VARCHAR(128)," +//内容
                " FOREIGN KEY(ng_id) REFERENCES tb_note_group(_id) ON DELETE SET NULL ON UPDATE CASCADE);");
        //日程类型
        db.execSQL("CREATE TABLE tb_schedule_group(" +
                " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +//序号
                " name TEXT NOT NULL UNIQUE," +//名称
                " description CHAR(16));");//描述
        //日程
        db.execSQL("CREATE TABLE tb_schedule(" +
                " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +//序号
                " sg_id INTEGER," +//日程类型外键
                " title TEXT," +//标题
                " remind_date TEXT," +//提醒日期
                " content VARCHAR(128)," +//内容
                " FOREIGN KEY(sg_id) REFERENCES tb_schedule_group(_id) ON DELETE SET NULL ON UPDATE CASCADE);");