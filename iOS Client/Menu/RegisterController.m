//
//  RegisterController.m
//  Menu
//
//  Created by 孙翔宇 on 16/5/10.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import "RegisterController.h"
#import "ViewController.h"
#import "AFNetworking.h"

@interface RegisterController ()
@property (weak, nonatomic) IBOutlet UITextField *usernameTextField;
@property (weak, nonatomic) IBOutlet UITextField *pwdTextField;
- (IBAction)register:(UIButton *)sender;

@end

@implementation RegisterController

- (void)viewDidLoad {
    [super viewDidLoad];
    
}


- (IBAction)register:(UIButton *)sender {
    
    NSString *username = self.usernameTextField.text;
    NSString *pwd = self.pwdTextField.text;
    NSLog(@"%lu", username.hash);
    NSLog(@"%@", pwd);
    
    AFHTTPSessionManager *mgr = [[AFHTTPSessionManager alloc] init];
    mgr.responseSerializer = [AFJSONResponseSerializer serializer];
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    params[@"user"] = @(username.hash);
    NSString *url = @"";
    
    [mgr GET:url parameters:params success:^(NSURLSessionDataTask *task, id responseObject) {
        NSLog(@"%@", responseObject);
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"%@", error);
    }];
    
    [self performSegueWithIdentifier:@"map" sender:nil];
 }
@end
