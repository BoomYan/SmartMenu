//
//  MenuDetailsController.m
//  Menu
//
//  Created by 孙翔宇 on 16/4/8.
//  Copyright (c) 2016年 孙翔宇. All rights reserved.
//

#import "MenuDetailsController.h"
#import "AFNetworking.h"
#import "MJExtension.h"
#import "Menu.h"
#import "SMAnnotation.h"
#import "UIBarButtonItem+Extension.h"
#import "Venue.h"
#import "MenuCell.h"
#import "MenuReviewsController.h"
#import "AwesomeMenu.h"
#import "UIView+AutoLayout.h"
#import "collectController.h"

@interface MenuDetailsController ()<AwesomeMenuDelegate>

@property(nonatomic, strong) NSArray *menus;
@property(nonatomic, strong) NSArray *sortedMenus;
@property(nonatomic, strong) NSArray *sortedMenus2;

@end

@implementation MenuDetailsController


- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupNav];
    
    [self getMenuInfo];
    
    [self setupAwesomeMenu];
}

- (void)setupAwesomeMenu
{
    // 1.中间的item
    AwesomeMenuItem *startItem = [[AwesomeMenuItem alloc] initWithImage:[UIImage imageNamed:@"icon_pathMenu_background_highlighted"] highlightedImage:nil ContentImage:[UIImage imageNamed:@"icon_pathMenu_mainMine_normal"] highlightedContentImage:nil];
    
    // 2.周边的item
    AwesomeMenuItem *item0 = [[AwesomeMenuItem alloc] initWithImage:[UIImage imageNamed:@"bg_pathMenu_black_normal"] highlightedImage:nil ContentImage:[UIImage imageNamed:@"icon_pathMenu_collect_normal"] highlightedContentImage:[UIImage imageNamed:@"icon_pathMenu_collect_highlighted"]];
    AwesomeMenuItem *item1 = [[AwesomeMenuItem alloc] initWithImage:[UIImage imageNamed:@"bg_pathMenu_black_normal"] highlightedImage:nil ContentImage:[UIImage imageNamed:@"icon_pathMenu_collect_normal"] highlightedContentImage:[UIImage imageNamed:@"icon_pathMenu_collect_highlighted"]];
    AwesomeMenuItem *item2 = [[AwesomeMenuItem alloc] initWithImage:[UIImage imageNamed:@"bg_pathMenu_black_normal"] highlightedImage:nil ContentImage:[UIImage imageNamed:@"icon_pathMenu_collect_normal"] highlightedContentImage:[UIImage imageNamed:@"icon_pathMenu_collect_highlighted"]];
    AwesomeMenuItem *item3 = [[AwesomeMenuItem alloc] initWithImage:[UIImage imageNamed:@"bg_pathMenu_black_normal"] highlightedImage:nil ContentImage:[UIImage imageNamed:@"icon_pathMenu_collect_normal"] highlightedContentImage:[UIImage imageNamed:@"icon_pathMenu_collect_highlighted"]];
    
    NSArray *items = @[item0, item1, item2, item3];
    AwesomeMenu *menu = [[AwesomeMenu alloc] initWithFrame:CGRectZero startItem:startItem optionMenus:items];
    menu.alpha = 0.5;
    // 设置菜单的活动范围
    menu.menuWholeAngle = M_PI_2;
    // 设置开始按钮的位置
    menu.startPoint = CGPointMake(75, 750);
    // 设置代理
    menu.delegate = self;
    // 不要旋转中间按钮
    menu.rotateAddButton = NO;
    [self.view addSubview:menu];
    
    // 设置菜单永远在左下角
    [menu autoPinEdgeToSuperviewEdge:ALEdgeLeft withInset:0];
    [menu autoPinEdgeToSuperviewEdge:ALEdgeBottom withInset:0];
    [menu autoSetDimensionsToSize:CGSizeMake(200, 200)];
}


- (void)awesomeMenu:(AwesomeMenu *)menu didSelectIndex:(NSInteger)idx
{
    // 半透明显示
    menu.alpha = 0.5;
    
//    // 替换菜单的图片
//    menu.contentImage = [UIImage imageNamed:@"icon_pathMenu_mainMine_normal"];
    
    switch (idx) {
        case 1: { // 收藏
            UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:[[collectController alloc] init]];
            [self presentViewController:nav animated:YES completion:nil];
            break;
        }
        default:
            break;
    }
}


-(void)setupNav
{
    self.navigationItem.title = @"Hot Menus";
    self.navigationController.navigationBar.barTintColor = [UIColor orangeColor];
    UIBarButtonItem *rightItem = [UIBarButtonItem itemWithTarget:self action:@selector(recommend) image:@"icon_map" highImage:@"icon_map_highlighted"];
    self.navigationItem.rightBarButtonItem = rightItem;
    
}

-(void)recommend
{
    NSLog(@"recommend");
    self.sortedMenus = [self.menus sortedArrayUsingComparator:^NSComparisonResult(Menu *obj1, Menu *obj2) {
        NSNumber *recom1 = [NSNumber numberWithInt:obj1.popularity];
        NSNumber *recom2 = [NSNumber numberWithInt:obj2.popularity];
        
        NSComparisonResult res = [recom1 compare:recom2];
        
        return res;
    }];
    
    
    [self.tableView reloadData];
}




- (IBAction)backTo:(UIBarButtonItem *)sender {
    
    [self dismissViewControllerAnimated:YES completion:nil];
}


#pragma mark - tableViewDelegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.sortedMenus.count;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    MenuCell *menuCell = [MenuCell cellWithTableView:tableView];
    
    Menu *menu = self.sortedMenus[indexPath.row];
    
    
    menuCell.menu = menu;
    
    return menuCell;
    
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 60;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    MenuReviewsController *menuReviews = [[MenuReviewsController alloc] init];
    Menu *menu = self.sortedMenus[indexPath.row];
    menuReviews.menu = menu;
    UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:menuReviews];
    [self presentViewController:nav animated:YES completion:nil];
    
}

//http://smartmenu2.us-east-1.elasticbeanstalk.com/getMenu

- (void)getMenuInfo
{
    AFHTTPSessionManager *mgr = [[AFHTTPSessionManager alloc] init];
    mgr.responseSerializer = [AFJSONResponseSerializer serializer];
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    NSString *url = @"http://smartmenu-backend.us-east-1.elasticbeanstalk.com/getMenu";
    //    [url stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    params[@"userID"] = @"00001";
    params[@"restaurantID"] = self.annotation.venue.restaurantID;
    [mgr GET:url parameters:params success:^(NSURLSessionDataTask *task, id responseObject) {
        
        NSLog(@"%@", responseObject);
        self.menus = [Menu objectArrayWithKeyValuesArray:responseObject[@"dishes"]];
        
        self.sortedMenus = [self.menus sortedArrayUsingComparator:^NSComparisonResult(Menu *obj1, Menu *obj2) {
            
            NSNumber *popularity1 = [NSNumber numberWithInt:obj1.popularity];
            NSNumber *popularity2 = [NSNumber numberWithInt:obj2.popularity];
            popularity1= (popularity1 == nil)?[NSNumber numberWithInt:0]:popularity1;
            popularity2= (popularity2 == nil)?[NSNumber numberWithInt:0]:popularity2;
            NSComparisonResult result = [popularity2 compare:popularity1];
            return result;

        }];
        NSLog(@"%@",self.sortedMenus);
        
        
//        self.annotation.venue = [Venue objectWithKeyValues:responseObject[@"response"][@"venue"]];
        [self.tableView reloadData];
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"%@", error);
    }];
}




@end
