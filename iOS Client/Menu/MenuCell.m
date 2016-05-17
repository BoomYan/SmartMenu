//
//  MenuCell.m
//  Menu
//
//  Created by 孙翔宇 on 16/5/6.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import "MenuCell.h"
#import "Venue.h"
#import "Menu.h"
#import "MenuTool.h"
#import "MBProgressHUD+MJ.h"

@interface MenuCell()

@property (weak, nonatomic) IBOutlet UILabel *menuNameLabel;

@property (weak, nonatomic) IBOutlet UILabel *menuPriceLabel;


- (IBAction)collect:(UIButton *)sender;


@end

@implementation MenuCell

- (void)awakeFromNib {
    
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

}

+(MenuCell *)cellWithTableView:(UITableView *)tableView
{
//    static NSString *ID = @"cell";
//    MenuCell *menuCell = [tableView dequeueReusableCellWithIdentifier:ID];
//    if (menuCell == nil) {
//        menuCell = [[[NSBundle mainBundle] loadNibNamed:@"MenuCell" owner:nil options:nil] lastObject];
//    }
    MenuCell *menuCell = [[[NSBundle mainBundle] loadNibNamed:@"MenuCell" owner:nil options:nil] lastObject];
    menuCell.selectionStyle = UITableViewCellSelectionStyleGray;
    
    return menuCell;
}

- (void)setMenu:(Menu *)menu
{
    _menu = menu;
    
    self.menuNameLabel.text = menu.name;
    self.menuNameLabel.font = [UIFont systemFontOfSize:14];
    self.menuPriceLabel.text = menu.price;
    
    self.recomendLabel.text = [NSString stringWithFormat:@"S:%d",menu.recommendation];

}

- (IBAction)collect:(UIButton *)sender {
    NSMutableDictionary *info = [NSMutableDictionary dictionary];
    info[@"MTCollectDealKey"] = self.menu;
    
    if (self.recommendButton.isSelected) { // 取消收藏
        [MenuTool removeCollectDeal:self.menu];
        [MBProgressHUD showSuccess:@"cancel success" toView:self];
        
        info[@"MTIsCollectKey"] = @NO;
    } else { // 收藏
        [MenuTool addCollectDeal:self.menu];
        [MBProgressHUD showSuccess:@"collect success" toView:self];
        
        info[@"MTIsCollectKey"] = @YES;
    }
    
    // 按钮的选中取反
    self.recommendButton.selected = !self.recommendButton.isSelected;
    
    [[NSNotificationCenter defaultCenter] postNotificationName:@"MTCollectStateDidChangeNotification" object:nil userInfo:info];
    
}
@end
