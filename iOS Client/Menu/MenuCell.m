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

@interface MenuCell()

@property (weak, nonatomic) IBOutlet UILabel *menuNameLabel;

@property (weak, nonatomic) IBOutlet UILabel *menuPriceLabel;

@property (weak, nonatomic) IBOutlet UIButton *recommendButton;


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
    self.recommendButton.titleLabel.text = @"5";

}

@end
