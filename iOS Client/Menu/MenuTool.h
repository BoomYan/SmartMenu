//
//  MenuTool.h
//  Menu
//
//  Created by 孙翔宇 on 16/5/12.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import <Foundation/Foundation.h>
@class Menu;

@interface MenuTool : NSObject

+ (void)addCollectDeal:(Menu *)menu;


+ (void)removeCollectDeal:(Menu *)menu;


+ (BOOL)isCollected:(Menu *)menu;

+ (int)collectDealsCount;

+ (NSArray *)collectDeals;


@end
