//
//  Menu.h
//  smartMenu
//
//  Created by 孙翔宇 on 16/4/7.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Menu : NSObject

//@property(nonatomic, copy) NSString *price;
//@property(nonatomic, copy) NSString *name;
//@property(nonatomic, assign) NSInteger reviewsCount;
//@property(nonatomic, copy) NSString *type;

@property(nonatomic, copy) NSString *price;
@property(nonatomic, copy) NSString *name;
@property(nonatomic, assign) int dishID;
@property(nonatomic, assign) int recommendation;
@property(nonatomic, assign) int popularity;

//-(NSComparisonResult)compareMenu:(Menu *)menu;

@end
