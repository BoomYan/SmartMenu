//
//  collectController.m
//  Menu
//
//  Created by 孙翔宇 on 16/5/12.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import "collectController.h"
#import "UIBarButtonItem+Extension.h"
#import "MenuTool.h"
#import "Menu.h"

@interface collectController ()

@property (nonatomic, strong) NSMutableArray *menus;
@property (nonatomic, assign) int currentPage;

@end

@implementation collectController


-(NSMutableArray *)menus
{
    if (!_menus) {
        self.menus = [MenuTool collectDeals];
    }
    return _menus;
}



- (void)viewDidLoad {
    [super viewDidLoad];
    
    UIBarButtonItem *leftItem = [UIBarButtonItem itemWithTarget:self action:@selector(back) image:@"icon_back" highImage:@"icon_back_highlighted"];
    self.navigationItem.leftBarButtonItem = leftItem;
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(collectStateChange:) name:@"MTCollectStateDidChangeNotification" object:nil];
    
}

-(void)back
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)collectStateChange:(NSNotification *)notification
{
        if ([notification.userInfo[@"MTIsCollectKey"] boolValue]) {
            // 收藏成功
            [self.menus insertObject:notification.userInfo[@"MTCollectDealKey"] atIndex:0];
            NSLog(@"%@", self.menus);
        } else {
            // 取消收藏成功
            [self.menus removeObject:notification.userInfo[@"MTCollectDealKey"]];
        }
    
        [self.tableView reloadData];
//    [self.menus removeAllObjects];
//    
//    self.currentPage = 0;
//    [self loadMoreDeal];
}



-(void)loadMoreDeal
{
    
//    // 1.增加页码
//    self.currentPage++;
    
    // 2.增加新数据
    [self.menus addObjectsFromArray:[MenuTool collectDeals]];
    
    // 3.刷新表格
    [self.tableView reloadData];
}

#pragma mark - Table view data source


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
//    [self.menus addObjectsFromArray:[MenuTool collectDeals]];
//    NSLog(@"%lu", (unsigned long)self.menus.count);
    return self.menus.count;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
    Menu *menu = self.menus[indexPath.row];
    cell.textLabel.text = menu.name;
    return cell;
}



@end
