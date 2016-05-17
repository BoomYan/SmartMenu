//
//  MenuReviewsController.m
//  Menu
//
//  Created by 孙翔宇 on 16/5/6.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import "MenuReviewsController.h"
#import "SMAnnotation.h"
#import "Venue.h"
#import "UIBarButtonItem+Extension.h"
#import "AFNetworking.h"
#import "Menu.h"
#import "MJExtension.h"
#import "Review.h"
#import <Social/Social.h>

@interface MenuReviewsController ()<UIWebViewDelegate>

@property(nonatomic, strong) NSArray *reviews;

@end

@implementation MenuReviewsController



- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    UIBarButtonItem *leftItem = [UIBarButtonItem itemWithTarget:self action:@selector(back) image:@"icon_back" highImage:@"icon_back_highlighted"];
    UIBarButtonItem *rightItem = [UIBarButtonItem itemWithTarget:self action:@selector(share) image:@"icon_map" highImage:@"icon_map_highlighted"];
    self.navigationItem.rightBarButtonItem = rightItem;
    self.navigationItem.leftBarButtonItem = leftItem;
    
    
    self.tableView.estimatedRowHeight = 66;
    [self getReivews];
    
    
}


-(void)getReivews
{
    
    AFHTTPSessionManager *mgr = [[AFHTTPSessionManager alloc] init];
    mgr.responseSerializer = [AFJSONResponseSerializer serializer];
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    params[@"userID"] = @"00001";
    params[@"dishID"] = [NSString stringWithFormat:@"%d",self.menu.dishID ];
    NSLog(@"%d", self.menu.dishID);
    NSString *url = @"http://smartmenu-backend.us-east-1.elasticbeanstalk.com/getReviews";
    [mgr GET:url parameters:params success:^(NSURLSessionDataTask *task, id responseObject) {
        NSLog(@"%@", responseObject);
        
        self.reviews = [Review objectArrayWithKeyValuesArray:responseObject[@"reviews"]];
        
        [self.tableView reloadData];
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"%@",error);
    }];
    
    
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.reviews.count;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
    Review *review = self.reviews[indexPath.row];
    
    cell.textLabel.numberOfLines = 0;
    cell.textLabel.text = review.text;
    return cell;
    
}

-(void)share
{
    if (![SLComposeViewController isAvailableForServiceType:SLServiceTypeFacebook]) {
        NSLog(@"不可用");
        return;
    }
    
    
    // 创建控制器，并设置ServiceType（指定分享平台）
    SLComposeViewController *composeVC = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeFacebook];
    // 添加要分享的图片
    [composeVC addImage:[UIImage imageNamed:@"Nameless"]];
    // 添加要分享的文字
    [composeVC setInitialText:@"share to PUTClub"];
    // 添加要分享的url
    [composeVC addURL:[NSURL URLWithString:@"http://www.putclub.com"]];
    // 弹出分享控制器
    [self presentViewController:composeVC animated:YES completion:nil];
    // 监听用户点击事件
    composeVC.completionHandler = ^(SLComposeViewControllerResult result){
        if (result == SLComposeViewControllerResultDone) {
            NSLog(@"点击了发送");
        }
        else if (result == SLComposeViewControllerResultCancelled)
        {
            NSLog(@"点击了取消");
        }
    };
}

-(void)back
{
    [self dismissViewControllerAnimated:YES completion:nil];
}





@end
