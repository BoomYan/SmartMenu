//
//  Venue.h
//  smartMenu
//
//  Created by 孙翔宇 on 16/4/6.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import <Foundation/Foundation.h>

@class SMLocation;
@class Menu;

@interface Venue : NSObject

@property(nonatomic, copy) NSString *restaurantID;
@property(nonatomic, copy) NSString *name;
//@property(nonatomic, strong) NSArray *categories;
//@property(nonatomic, strong) SMLocation *location;
@property(nonatomic, copy) NSString *location;
@property(nonatomic, assign) BOOL hasMenu;
@property(nonatomic, strong) Menu *menu;
@property(nonatomic, copy) NSString *canonicalUrl;

@end
