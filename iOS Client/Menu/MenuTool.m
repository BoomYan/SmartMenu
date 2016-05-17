//
//  MenuTool.m
//  Menu
//
//  Created by 孙翔宇 on 16/5/12.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import "MenuTool.h"
#import "FMDB.h"
#import "Menu.h"

@implementation MenuTool

static FMDatabase *_db;

+ (void)initialize
{
    NSString *file = [[NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) lastObject] stringByAppendingPathComponent:@"menu.sqlite"];
    NSLog(@"%@", file);
    _db = [FMDatabase databaseWithPath:file];
    if (![_db open]) return;
    
    [_db executeUpdate:@"CREATE TABLE IF NOT EXISTS t_collect_menu(id integer PRIMARY KEY, menu blob NOT NULL, menu_id integer NOT NULL);"];

}

+(void)addCollectDeal:(Menu *)menu
{
    NSData *data = [NSKeyedArchiver archivedDataWithRootObject:menu];
    [_db executeUpdateWithFormat:@"INSERT INTO t_collect_menu(menu, menu_id) VALUES(%@, %d);", data, menu.dishID];

}

+(void)removeCollectDeal:(Menu *)menu
{
    [_db executeUpdateWithFormat:@"DELETE FROM t_collect_menu WHERE menu_id = %d;", menu.dishID];
}

+ (BOOL)isCollected:(Menu *)menu
{
    FMResultSet *set = [_db executeQueryWithFormat:@"SELECT count(*) AS menu_count FROM t_collect_menu WHERE menu_id = %d;", menu.dishID];
    [set next];
    return [set intForColumn:@"menu_count"] == 1;
}

+(int)collectDealsCount
{
    FMResultSet *set = [_db executeQueryWithFormat:@"SELECT count(*) AS menu_count FROM t_collect_menu;"];
    [set next];
    return [set intForColumn:@"menu_count"];
}

+(NSArray *)collectDeals
{
//    int size = 10;
//    int pos = (page - 1) * size;
    FMResultSet *set = [_db executeQueryWithFormat:@"SELECT * FROM t_collect_menu ORDER BY id DESC;"];
    NSMutableArray *menus = [NSMutableArray array];
    while (set.next) {
        Menu *menu = [NSKeyedUnarchiver unarchiveObjectWithData:[set objectForColumnName:@"menu"]];
        NSLog(@"%@", menu.name);
        [menus addObject:menu];
    }
    return menus;

}

@end
