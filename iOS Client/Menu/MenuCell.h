//
//  MenuCell.h
//  Menu
//
//  Created by 孙翔宇 on 16/5/6.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import <UIKit/UIKit.h>

@class Menu;

@interface MenuCell : UITableViewCell

@property(nonatomic, strong) Menu *menu;

@property (weak, nonatomic) IBOutlet UIButton *recommendButton;
@property (weak, nonatomic) IBOutlet UILabel *recomendLabel;

+(MenuCell *)cellWithTableView:(UITableView *)tableView;

@end
