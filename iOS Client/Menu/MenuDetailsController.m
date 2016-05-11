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
#import "Venue.h"
#import "MenuCell.h"
#import "MenuReviewsController.h"

@interface MenuDetailsController ()

@property(nonatomic, strong) NSArray *menus;

@end

@implementation MenuDetailsController




- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupNav];
    
    [self getMenuInfo];
}

-(void)setupNav
{
    self.navigationItem.title = @"Hot Menus";
    self.navigationController.navigationBar.barTintColor = [UIColor orangeColor];
    
    
}




- (IBAction)backTo:(UIBarButtonItem *)sender {
    
    [self dismissViewControllerAnimated:YES completion:nil];
}


#pragma mark - tableViewDelegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.menus.count;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    MenuCell *menuCell = [MenuCell cellWithTableView:tableView];
    
    Menu *menu = self.menus[indexPath.row];
    
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
    menuReviews.annotation = self.annotation;
    UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:menuReviews];
    [self presentViewController:nav animated:YES completion:nil];
    
}

//http://smartmenu2.us-east-1.elasticbeanstalk.com/getMenu

- (void)getMenuInfo
{
    AFHTTPSessionManager *mgr = [[AFHTTPSessionManager alloc] init];
    mgr.responseSerializer = [AFJSONResponseSerializer serializer];
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    NSString *url = @"http://smartmenu2.us-east-1.elasticbeanstalk.com/getMenu";
    //    [url stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    params[@"rID"] = self.annotation.venue.venue_id;
    [mgr GET:url parameters:params success:^(NSURLSessionDataTask *task, id responseObject) {
        
        self.menus = [Menu objectArrayWithKeyValuesArray:responseObject];
        
//        self.annotation.venue = [Venue objectWithKeyValues:responseObject[@"response"][@"venue"]];
        [self.tableView reloadData];
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"%@", error);
    }];
}




@end
