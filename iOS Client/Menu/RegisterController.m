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

@property (weak, nonatomic) IBOutlet UIButton *plaza;
@property (weak, nonatomic) IBOutlet UIButton *vegetable;

@property (weak, nonatomic) IBOutlet UIButton *meat;

- (IBAction)checkPlaza:(UIButton *)sender;

- (IBAction)checkVege:(UIButton *)sender;

- (IBAction)checkMeat:(UIButton *)sender;

- (IBAction)submit:(UIButton *)sender;


@end

@implementation RegisterController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self.plaza setImage:[UIImage imageNamed:@"bg_dealcell"] forState:UIControlStateNormal];
    [self.plaza setImage:[UIImage imageNamed:@"ic_choosed"] forState:UIControlStateSelected];
    [self.vegetable setImage:[UIImage imageNamed:@"bg_dealcell"] forState:UIControlStateNormal];
    [self.vegetable setImage:[UIImage imageNamed:@"ic_choosed"] forState:UIControlStateSelected];
    [self.meat setImage:[UIImage imageNamed:@"bg_dealcell"] forState:UIControlStateNormal];
    [self.meat setImage:[UIImage imageNamed:@"ic_choosed"] forState:UIControlStateSelected];
}




- (IBAction)checkPlaza:(UIButton *)sender {
    
    self.plaza.selected = !self.plaza.selected;
}

- (IBAction)checkVege:(UIButton *)sender {
    self.vegetable.selected = !self.vegetable.selected;
}


- (IBAction)checkMeat:(UIButton *)sender {
    self.meat.selected = !self.meat.selected;
}

-(IBAction)submit:(UIButton *)sender {
    
    
     NSString *username = self.usernameTextField.text;
    [self performSegueWithIdentifier:@"map" sender:nil];
}
@end
