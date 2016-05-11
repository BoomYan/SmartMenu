//
//  SMLocation.h
//  smartMenu
//
//  Created by 孙翔宇 on 16/4/6.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SMLocation : NSObject

@property (nonatomic, assign) float lat;
/** 经度 */
@property (nonatomic, assign) float lng;

@property(nonatomic, copy) NSString *city;
@property(nonatomic, copy) NSString *address;

@end
