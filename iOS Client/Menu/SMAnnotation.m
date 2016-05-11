//
//  SMAnnotation.m
//  smartMenu
//
//  Created by 孙翔宇 on 16/4/6.
//  Copyright © 2016年 孙翔宇. All rights reserved.
//

#import "SMAnnotation.h"

@implementation SMAnnotation

- (BOOL)isEqual:(SMAnnotation *)other
{
    return [self.title isEqualToString:other.title];
}

@end
